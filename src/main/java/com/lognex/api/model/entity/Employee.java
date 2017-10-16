package com.lognex.api.model.entity;

import com.lognex.api.model.entity.attribute.Attribute;
import com.lognex.api.util.ID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Employee extends Agent {
    private String uid;
    private String email;
    private String phone;
    private String middleName;
    private String lastName;
    private String firstName;
    private String fullName;
    private String shortFio;
    private Cashier cashier;
    private List<Attribute> attributes;

    public Employee(ID id) {
        setId(id);
    }

}
