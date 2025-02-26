package reactive.rest.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactive.rest.model.Product;
import reactive.rest.repository.ProductRepository;
import reactor.core.publisher.Mono;

/**
 * The {@code ProductPaginationController} class is a REST controller responsible for handling the pagination of products.
 *
 * <p>It provides the endpoint "/products" to retrieve products with pagination support.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 *     GET /products?page=0&size=10
 * }</pre>
 *
 * <p>It requires an instance of {@code ProductRepository} to be properly initialized, which provides access to the database.</p>
 *
 * <p>Example instantiation:</p>
 * <pre>{@code
 *     ProductRepository productRepository = new ProductRepository();
 *     ProductPaginationController controller = new ProductPaginationController(productRepository);
 * }</pre>
 */
@RestController
@RequiredArgsConstructor
public class ProductPaginationController {

    private final ProductRepository productRepository;

    @GetMapping("/products")
    public Mono<Page<Product>> findAllProducts(Pageable pageable) {
        return this.productRepository.findAllBy(pageable)
          .collectList()
          .zipWith(this.productRepository.count())
          .map(p -> new PageImpl<>(p.getT1(), pageable, p.getT2()));
    }

}
