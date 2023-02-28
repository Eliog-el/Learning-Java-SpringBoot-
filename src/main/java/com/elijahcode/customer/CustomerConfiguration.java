//package com.elijahcode.customer;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class CustomerConfiguration {
//
//    @Value("${app.useCustomerListDataAccessService:false}")
//    private Boolean useCustomerListDataAccessService;
//
//    CommandLineRunner commandLineRunner() {
//        return args -> {
//            System.out.println("Command line runner up!");
//        };
//    }
//
//    CustomerDao customerDao() {
//        return useCustomerListDataAccessService ?
//                new CustomerListDataAccessService() :
//                new CustomerJPADataAccessService();
//    }
//}
