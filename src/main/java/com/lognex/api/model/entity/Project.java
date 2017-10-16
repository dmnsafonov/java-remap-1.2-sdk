package com.lognex.api.model.entity;

import com.lognex.api.model.base.AbstractEntityLegendable;
import com.lognex.api.model.entity.attribute.Attribute;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Project extends AbstractEntityLegendable {
    private boolean archived;
    private List<Attribute> attributes;
}
