package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {
    private Map<String,Order> orderMap=new HashMap<>();
    private Map<String,DeliveryPartner> partnerMap=new HashMap<>();
    private Map<String, List<String>> orderPartnerMap=new HashMap<>();
    private Map<String,String> opMap=new HashMap<>();
    public void addOrder(Order order) {
        orderMap.put(order.getId(),order);
    }

    public void addPartner(DeliveryPartner partner) {
        partnerMap.put(partner.getId(),partner);
    }

    public Optional<Order> getOrderById(String orderId) {
        if(orderMap.containsKey(orderId)){
            return Optional.of(orderMap.get(orderId));
        }
        return Optional.empty();
    }

    public Optional<DeliveryPartner> getPartnerById(String partnerId) {
        if(partnerMap.containsKey(partnerId)){
            return Optional.of(partnerMap.get(partnerMap));
        }
        return Optional.empty();
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        List<String> list=new ArrayList<>();
        list=orderPartnerMap.get(partnerId);
        list.add(orderId);
        orderPartnerMap.put(partnerId,list);
        opMap.put(orderId,partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return orderPartnerMap.get(partnerId).size();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderPartnerMap.get(partnerId);
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(orderMap.keySet());
    }

    public Integer getCountOfUnassignedOrders() {
        return orderMap.size()-opMap.size();
    }

    public void deletePartnerById(String partnerId) {
        partnerMap.remove(partnerId);
        orderPartnerMap.remove(partnerId);
    }

    public void unassignOrders(String orderId) {
        opMap.remove(orderId);
    }

    public String getPartnerId(String orderId) {
        return opMap.get(orderId);
    }

    public void removerOrderId(String orderId) {
        orderMap.remove(orderId);
        opMap.remove(orderId);
    }

    public void removeOrderId(String partnerId,String orderId) {
        List<String> list=orderPartnerMap.get(partnerId);
        list.remove(orderId);
        orderPartnerMap.put(partnerId,list);
    }
}
