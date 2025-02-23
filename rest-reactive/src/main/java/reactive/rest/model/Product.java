package reactive.rest.model;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


/**
 * The {@code Product} class represents a product entity in a database.
 *
 * <p>It contains information about the product's ID, name, and price.</p>
 *
 * <p>This class provides the following functionalities:</p>
 * <ul>
 *   <li>Creation of a product with an ID, name, and price</li>
 *   <li>Retrieval and modification of the product's ID, name, and price</li>
 * </ul>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 *     Product product = new Product(UUID.randomUUID(), "Example Product", 9.99);
 *     UUID productId = product.getId();
 *     String productName = product.getName();
 *     double productPrice = product.getPrice();
 *     product.setPrice(19.99);
 * }</pre>
 *
 * <p>Note: This class uses the Lombok library to automatically generate getter, setter, and constructor methods.</p>
 *
 * @see Data
 * @see AllArgsConstructor
 * @see NoArgsConstructor
 *
 * @see javax.persistence.Table
 * @see javax.persistence.Id
 * @see NotNull
 * @see Size
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Product {

    @Id
    @Getter
    private UUID id;

    @NotNull
    @Size(max = 255, message = "The property 'name' must be less than or equal to 255 characters.")
    private String name;

    @NotNull
    private double price;
}
