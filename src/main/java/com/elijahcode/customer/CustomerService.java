package com.elijahcode.customer;

import com.elijahcode.exception.DuplicateResourceException;
import com.elijahcode.exception.RequestValidationException;
import com.elijahcode.exception.ResourceNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    @Autowired
    public CustomerService(@Qualifier("jpa") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers(){
        return customerDao.selectAllCustomers();
    }

    public Customer getCustomers(Integer id){
        return customerDao.selectCustomersById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(id)
                ));
    }

    public void addCustomer(@NotNull CustomerRegistrationRequest customerRegistrationRequest) {
        // check if email exist
        String email = customerRegistrationRequest.email();


        if (customerDao.existsPersonWithEmail(email)) {
            System.out.println(email);

            throw new DuplicateResourceException(
                    "email already taken"
            );
        }
        // add
        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age(),
                customerRegistrationRequest.password()
        );
        customerDao.insertCustomer(
                customer
        );
    }

    public void deleteCustomerById(Integer customerId) {
        if (!customerDao.existsPersonWithId(customerId)) {
            throw new ResourceNotFoundException(
                    "customer with id [%s] not found".formatted(customerId)
            );
        };

        customerDao.deleteCustomerById(customerId);
    }


    public void updateCustomerById(Integer customerId, CustomerUpdateRequest customerUpdateRequest) {

        Customer customer = getCustomers(customerId);

        boolean changes = false;

        if (customerUpdateRequest.name() != null && !customerUpdateRequest.name().equals(customer.getName())) {
            customer.setName(customerUpdateRequest.name());
            changes = true;
        }

        if (customerUpdateRequest.age() != null && !customerUpdateRequest.age().equals(customer.getAge())) {
            customer.setAge(customerUpdateRequest.age());
            changes = true;
        }

        if (customerUpdateRequest.email() != null && !customerUpdateRequest.email().equals(customer.getAge())) {
            if (customerDao.existsPersonWithEmail(customerUpdateRequest.email())) {
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }
            customer.setEmail(customerUpdateRequest.email());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException(
                    "no data changes found"
            );
        }

        customerDao.updateCustomer(customer);

    }
}
