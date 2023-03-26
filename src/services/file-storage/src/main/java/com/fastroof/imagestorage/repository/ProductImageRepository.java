package com.fastroof.imagestorage.repository;

import com.fastroof.imagestorage.entity.ProductImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends CrudRepository<ProductImage, Long> {
}
