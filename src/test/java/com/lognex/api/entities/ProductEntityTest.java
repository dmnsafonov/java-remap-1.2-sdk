package com.lognex.api.entities;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.products.*;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class ProductEntityTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, LognexApiException {
        ProductEntity product = new ProductEntity();
        product.setName("product_" + randomString(3) + "_" + new Date().getTime());
        product.setArchived(false);
        product.setDescription(randomString());
        product.setArticle(randomString());
        product.setWeight(randomDouble(1, 5, 2));

        api.entity().product().post(product);

        ListEntity<ProductEntity> updatedEntitiesList = api.entity().product().get(filterEq("name", product.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ProductEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(product.getName(), retrievedEntity.getName());
        assertEquals(product.getArchived(), retrievedEntity.getArchived());
        assertEquals(product.getDescription(), retrievedEntity.getDescription());
        assertEquals(product.getArticle(), retrievedEntity.getArticle());
        assertEquals(product.getWeight(), retrievedEntity.getWeight());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedResponse metadata = api.entity().product().metadata();
        assertTrue(metadata.getCreateShared());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        ProductEntity originalProduct = (ProductEntity) originalEntity;
        ProductEntity retrievedProduct = (ProductEntity) retrievedEntity;

        assertEquals(originalProduct.getName(), retrievedProduct.getName());
        assertEquals(originalProduct.getDescription(), retrievedProduct.getDescription());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        ProductEntity originalProduct = (ProductEntity) originalEntity;
        ProductEntity updatedProduct = (ProductEntity) updatedEntity;

        assertNotEquals(originalProduct.getName(), updatedProduct.getName());
        assertEquals(changedField, updatedProduct.getName());
        assertEquals(originalProduct.getDescription(), updatedProduct.getDescription());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().product();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return ProductEntity.class;
    }
}
