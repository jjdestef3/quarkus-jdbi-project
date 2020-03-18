package com.mbronshteyn.quarkus.dao;

import com.mbronshteyn.quarkus.entity.Employee;
import com.mbronshteyn.quarkus.entity.mapping.EmployeeMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Date;

@RegisterRowMapper(EmployeeMapper.class)
public interface EmployeeDao {

    @SqlUpdate("CREATE TABLE \"EMPLOYEE\" ( \"ID\" bigint NOT NULL, \"FIRST_NAME\" character varying(255) NOT NULL, " +
            "\"LAST_NAME\" character varying(255) NOT NULL, \"BIRTHDAY\" date NOT NULL, " +
            "\"ADDRESS_ID\" bigint NOT NULL, " +
            "CONSTRAINT \"EMPLOYEE_pkey\" " +
            "PRIMARY KEY (\"ID\"), " +
            "CONSTRAINT \"EMPLOYEE_ID_fkey\" " +
            "FOREIGN KEY (\"ID\") )")
    void createEmployeeTable();

    @SqlUpdate("INSERT into PUBLIC.EMPLOYEE { ID, FIRST_NAME, LAST_NAME, BIRTHDAY, ADDRESS_ID } " +
            "VALUES ( :id, :firstName, :lastName, :birthday, :addressId)")
    void add(@Bind("id") Long id, @Bind("firstName") String firstName,
             @Bind("lastName") String lastName, @Bind("birthday") Date birthday,
             @Bind("addressId") Long addressId);

    @SqlQuery("SELECT * FROM PUBLIC.EMPLOYEE WHERE ID = :id")
    Employee findById(@Bind("id") Long id);

    @SqlQuery("SELECT * FROM PUBLIC.EMPLOYEE WHERE LAST_NAME = :lastName")
    Employee findByLastName(@Bind("lastName") String lastName);

    @SqlQuery("DELETE * FROM PUBLIC.EMPLOYEE WHERE ID = :id")
    void remove(@Bind("id") Long id);

    /**
     * Make sure we close the connection !!!
     */
    void close();
}