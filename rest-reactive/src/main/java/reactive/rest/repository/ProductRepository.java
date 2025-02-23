package reactive.rest.repository;


import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;


import reactive.rest.model.Product;
import reactor.core.publisher.Flux;

/**
 * The {@code ProductRepository} interface represents a repository for managing products in a database.
 *
 * <p>It provides methods for retrieving products with pagination support.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 *     ProductRepository productRepository = new ProductRepositoryImpl();
 *     Flux<Product> products = productRepository.findAllBy(PageRequest.of(0, 10));
 * }</pre>
 *
 * @see Repository
 * @see ReactiveSortingRepository
 */
@Repository
public interface ProductRepository extends ReactiveSortingRepository<Product, UUID> {
    Flux<Product> findAllBy(Pageable pageable);
}
