package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner partner=new DeliveryPartner(partnerId);
        orderRepository.addPartner(partner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
       Order orderOpt=orderRepository.getOrderById(orderId);
        Optional<DeliveryPartner> partnerOpt=orderRepository.getPartnerById(partnerId);
        if(Objects.nonNull(orderOpt) && partnerOpt.isPresent()){
            DeliveryPartner currPartner=partnerOpt.get();
            Integer orders=currPartner.getNumberOfOrders();
            orders++;
            currPartner.setNumberOfOrders(orders);
            orderRepository.addPartner(currPartner);
            orderRepository.addOrderPartnerPair(orderId,partnerId);
        }
    }

    public Order getOrderById(String orderId){
        return orderRepository.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        Optional<DeliveryPartner> partnerOpt=orderRepository.getPartnerById(partnerId);
        if(partnerOpt.isPresent())
            return partnerOpt.get();
        return null;
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        return orderRepository.getCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        List<String> orders=orderRepository.getOrdersByPartnerId(partnerId);
        int currTime=TimeUtils.convertDeliveryTime(time);
        int count=0;
        for(String orderId:orders){
            int deliveryTime=orderRepository.getOrderById(orderId).getDeliveryTime();
            if(currTime<deliveryTime){
                count++;
            }
        }
        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        List<String> orders=orderRepository.getOrdersByPartnerId(partnerId);
        int max=0;
        for(String orderId:orders){
            int deliveryTime=orderRepository.getOrderById(orderId).getDeliveryTime();
            if(max<deliveryTime){
                max=deliveryTime;
            }
        }
        return TimeUtils.convertDeliveryTime(max);
    }

    public void deletePartnerById(String partnerId) {
        List<String> orders=orderRepository.getOrdersByPartnerId(partnerId);
        orderRepository.deletePartnerById(partnerId);
        for(String orderId:orders){
            orderRepository.unassignOrders(orderId);
        }
    }

    public void deleteOrderById(String orderId) {
        String partnerId=orderRepository.getPartnerId(orderId);
        orderRepository.removerOrderId(orderId);
        if(Objects.nonNull(partnerId)) {
            Optional<DeliveryPartner> currPartnerr=orderRepository.getPartnerById(partnerId);
            DeliveryPartner currPartner=currPartnerr.get();
            Integer orders=currPartner.getNumberOfOrders();
            orders--;
            currPartner.setNumberOfOrders(orders);
            orderRepository.addPartner(currPartner);
            orderRepository.removeOrderId(partnerId, orderId);
        }
    }
}
