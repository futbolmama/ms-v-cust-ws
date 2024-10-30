package dev.futbolmama.msvcustws.db.repository;

import dev.futbolmama.msvcustws.db.model.Customer;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CustomerRepository {
    private final JdbcClient jdbcClient;

    public CustomerRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Customer> findAll() {
        var sqlAll = "SELECT * FROM customer";
        return jdbcClient.sql(sqlAll).query(Customer.class).list();
    }

    public Optional<Customer> findById(Long id) {
        var sqlFind = "SELECT * FROM customer WHERE id = :id";
        return jdbcClient.sql(sqlFind).param("id", id).query(Customer.class).optional();
    }

    @Transactional
    public Long save(Customer customer) {
        String sqlSave = """
                insert into customer(f_name, m_name, l_name, email, phone)
                values(:fName,:mName,:lName,:email:,:phone) returning id
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql(sqlSave)
                  .param("fName", customer.fName())
                  .param("mName", customer.mName())
                  .param("lName", customer.lName())
                  .param("email", customer.email())
                  .param("phone", customer.phone())
                  .update(keyHolder);
        return keyHolder.getKeyAs(Long.class);
    }

    @Transactional
    public void update(Customer customer) {
        String sqlUpdate = "update customer set f_name = ?, m_name = ?, l_name = ?, email = ?, phone = ?, modify_date = ? where id = ?";
        int count = jdbcClient.sql(sqlUpdate)
                              .param(1, customer.fName())
                              .param(2, customer.mName())
                              .param(3, customer.lName())
                              .param(4, customer.email())
                              .param(5, customer.phone())
                              .param(6, Timestamp.from(customer.modifyDate()))
                              .param(7, customer.id())
                              .update();
        if (count == 0) {
            throw new RuntimeException("Customer not found when updating record");
        }
    }

    public void deleteById(Long id) {
        String sqlDelete = "delete from customer where id = ?";
        int count = jdbcClient.sql(sqlDelete).param(1, id).update();
        if (count == 0) {
            throw new RuntimeException("Customer not found when deleting record");
        }
    }

}
