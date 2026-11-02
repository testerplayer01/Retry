import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.ApiAssert;
import utils.BasicApi;
import utils.ConfigParser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Epic("Каталог товаров")
@Feature("REST API")
@Tag("RestAssuredHomework")
class RestAssuredHomework {
    private final BasicApi goodsApi = new BasicApi();
    private final ApiAssert apiAssert = new ApiAssert();
    private final List<Long> createdProductIds = new ArrayList<>();

    @AfterEach
    void deleteCreatedProducts() {
        List<Long> idsToDelete = new ArrayList<>(createdProductIds);
        createdProductIds.clear();

        for (Long productId : idsToDelete) {
            apiAssert.assertStatusCode(goodsApi.deleteProduct(productId), 200);
        }
    }

    @Test
    @Tag("smoke")
    void goodsListIsAvailable() {
        apiAssert.assertGoodsListAvailable(goodsApi.getGoodsList());
    }

    @Test
    void createdProductAppearsInGoodsList() {
        CreatedProduct product = createProduct();

        apiAssert.assertProductNamePresent(
                goodsApi.getGoodsList().jsonPath().getList("goods.name", String.class),
                product.name()
        );
    }

    @Test
    void productCanBeCreated() {
        CreatedProduct product = createProduct();

        apiAssert.assertProductCreated(product.response());
    }

    @Test
    void productCanBeReadById() {
        CreatedProduct product = createProduct();

        apiAssert.assertProductResponse(
                goodsApi.getProduct(product.id()),
                product.name()
        );
    }

    @Test
    void readingMissingProductReturnsNotFound() {
        apiAssert.assertStatusCode(goodsApi.getProduct(0), 404);
    }

    @Test
    void productWithoutNameIsRejected() {
        Response response = goodsApi.createProduct("", ConfigParser.getProductPriceAsDouble());

        apiAssert.assertStatusCode(response, 400);
    }

    @Test
    void productCanBeUpdated() {
        CreatedProduct product = createProduct();
        String updatedName = ConfigParser.getProductName() + "-" + UUID.randomUUID();

        apiAssert.assertStatusCode(
                goodsApi.updateProduct(
                        product.id(),
                        updatedName,
                        ConfigParser.getProductPriceAsDouble()
                ),
                200
        );
        apiAssert.assertProductNamePresent(
                goodsApi.getGoodsList().jsonPath().getList("goods.name", String.class),
                updatedName
        );
    }

    @Test
    void duplicateProductNameIsRejected() {
        CreatedProduct product = createProduct();
        CreatedProduct duplicateName = createProduct();

        apiAssert.assertStatusCode(
                goodsApi.updateProduct(
                        product.id(),
                        duplicateName.name(),
                        ConfigParser.getProductPriceAsDouble()
                ),
                400
        );
    }

    @Test
    void updatingMissingProductReturnsNotFound() {
        String updatedName = ConfigParser.getProductName() + "-" + UUID.randomUUID();

        apiAssert.assertStatusCode(
                goodsApi.updateProduct(
                        0,
                        updatedName,
                        ConfigParser.getProductPriceAsDouble()
                ),
                404
        );
    }

    @Test
    void productCanBeDeleted() {
        CreatedProduct product = createProduct();

        apiAssert.assertStatusCode(goodsApi.deleteProduct(product.id()), 200);
        createdProductIds.remove(product.id());
        apiAssert.assertProductNameAbsent(
                goodsApi.getGoodsList().jsonPath().getList("goods.name", String.class),
                product.name()
        );
    }

    @Test
    void deletingMissingProductReturnsNotFound() {
        apiAssert.assertStatusCode(goodsApi.deleteProduct(0), 404);
    }

    private CreatedProduct createProduct() {
        String name = ConfigParser.getProductName() + "-" + UUID.randomUUID();
        Response response = goodsApi.createProduct(
                name,
                ConfigParser.getProductPriceAsDouble()
        );
        apiAssert.assertProductCreated(response);

        long id = response.jsonPath().getLong("data.id");
        createdProductIds.add(id);
        return new CreatedProduct(id, name, response);
    }

    private record CreatedProduct(long id, String name, Response response) {
    }
}
