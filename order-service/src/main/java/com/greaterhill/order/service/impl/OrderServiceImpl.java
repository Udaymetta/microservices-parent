package com.greaterhill.order.service.impl;

import com.greaterhill.exception.InternalException;
import com.greaterhill.model.CommonResponseObject;
import com.greaterhill.order.dao.OrderHeaderDao;
import com.greaterhill.order.entity.OrderDetail;
import com.greaterhill.order.entity.OrderHeader;
import com.greaterhill.order.model.InventoryStockResponse;
import com.greaterhill.order.model.OrderLineItemsDto;
import com.greaterhill.order.model.OrderRequestDto;
import com.greaterhill.order.model.OrderResponseDto;
import com.greaterhill.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderHeaderDao orderHeaderDao;
    private final RestTemplate restTemplate;
    @Value("${app.secretkey}")
    private final String secretKey;

    @Override
    public CommonResponseObject createOrder(OrderRequestDto request) {
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomerName(request.getCustomerName());
        orderHeader.setPhoneNumber(request.getPhoneNumber());
        orderHeader.setOrderNumber(UUID.randomUUID().toString());
        orderHeader.setOrderDate(new Date());
        orderHeader.setSubTotal(request.getSubTotal());
        orderHeader.setOrderDetails(request.getLineItems().stream().map(line -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setItemId(line.getId());
            orderDetail.setQuantity(line.getQuantity());
            orderDetail.setPrice(line.getPrice());
            orderDetail.setOrderHeader(orderHeader);
            return orderDetail;
        }).toList());
        if (Boolean.TRUE.equals(getIsItemInStock(request.getLineItems()))) {
            orderHeaderDao.save(orderHeader);
            return CommonResponseObject.builder()
                    .status("ok")
                    .message("OrderNumber : " + orderHeader.getOrderNumber())
                    .build();
        } else {
            throw new InternalException("Item(s) are out of stock, Please try again later!");
        }
    }

    @Override
    public Object getOrders(String orderNumber) {
        if (orderNumber != null) {
            OrderHeader orderHeader = orderHeaderDao.findByOrderNumber(orderNumber);
            List<OrderLineItemsDto> lineItems = orderHeader.getOrderDetails().stream().map(orderDetail ->
                    new OrderLineItemsDto(orderDetail.getItemId(), orderDetail.getQuantity(),
                            orderDetail.getPrice())
            ).toList();
            return new OrderResponseDto(orderHeader.getOrderNumber(), orderHeader.getCustomerName(),
                    orderHeader.getPhoneNumber(), orderHeader.getOrderDate(), orderHeader.getSubTotal(), lineItems);
        }
        List<OrderHeader> orderHeaderList = orderHeaderDao.findAll();
        return orderHeaderList.stream().map(orderHeader -> {
            List<OrderLineItemsDto> lineItems = orderHeader.getOrderDetails().stream().map(orderDetail ->
                    new OrderLineItemsDto(orderDetail.getItemId(), orderDetail.getQuantity(),
                            orderDetail.getPrice())).toList();
            return new OrderResponseDto(orderHeader.getOrderNumber(), orderHeader.getCustomerName(),
                    orderHeader.getPhoneNumber(), orderHeader.getOrderDate(), orderHeader.getSubTotal(), lineItems);
        }).toList();
    }

    private Boolean getIsItemInStock(List<OrderLineItemsDto> lineItems) {
        List<Integer> itemIdList = lineItems.stream().map(OrderLineItemsDto::getId).toList();
        ResponseEntity<List<InventoryStockResponse>> response;
        String url = "http://inventory-service/api/inventory";
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        itemIdList.forEach(itemId -> uriBuilder.queryParam("itemIdList", itemId));
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, secretKey);
        response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, new HttpEntity<>(headers), new ParameterizedTypeReference<List<InventoryStockResponse>>() {
        });
        if (response.getBody() != null && !response.getBody().isEmpty()) {
            if (response.getBody().stream().allMatch(InventoryStockResponse::getInStock)) {
                if (lineItems.stream().allMatch(line -> response.getBody().stream().allMatch(res -> res.getId().equals(line.getId()) && res.getQuantity() >= line.getQuantity()))) {
                    return true;
                } else {
                    throw new InternalException("Few items in your list are not available for the requested quantity");
                }
            } else {
                return false;
            }
        } else
            return false;
    }
}