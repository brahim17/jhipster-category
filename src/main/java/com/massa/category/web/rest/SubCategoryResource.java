package com.massa.category.web.rest;

import com.massa.category.domain.SubCategory;
import com.massa.category.repository.SubCategoryRepository;
import com.massa.category.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.massa.category.domain.SubCategory}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SubCategoryResource {

    private final Logger log = LoggerFactory.getLogger(SubCategoryResource.class);

    private static final String ENTITY_NAME = "categorySubCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubCategoryRepository subCategoryRepository;

    public SubCategoryResource(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    /**
     * {@code POST  /sub-categories} : Create a new subCategory.
     *
     * @param subCategory the subCategory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subCategory, or with status {@code 400 (Bad Request)} if the subCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sub-categories")
    public ResponseEntity<SubCategory> createSubCategory(@RequestBody SubCategory subCategory) throws URISyntaxException {
        log.debug("REST request to save SubCategory : {}", subCategory);
        if (subCategory.getId() != null) {
            throw new BadRequestAlertException("A new subCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubCategory result = subCategoryRepository.save(subCategory);
        return ResponseEntity.created(new URI("/api/sub-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sub-categories} : Updates an existing subCategory.
     *
     * @param subCategory the subCategory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subCategory,
     * or with status {@code 400 (Bad Request)} if the subCategory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subCategory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sub-categories")
    public ResponseEntity<SubCategory> updateSubCategory(@RequestBody SubCategory subCategory) throws URISyntaxException {
        log.debug("REST request to update SubCategory : {}", subCategory);
        if (subCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubCategory result = subCategoryRepository.save(subCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subCategory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sub-categories} : get all the subCategories.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subCategories in body.
     */
    @GetMapping("/sub-categories")
    public ResponseEntity<List<SubCategory>> getAllSubCategories(Pageable pageable) {
        log.debug("REST request to get a page of SubCategories");
        Page<SubCategory> page = subCategoryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sub-categories/:id} : get the "id" subCategory.
     *
     * @param id the id of the subCategory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subCategory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sub-categories/{id}")
    public ResponseEntity<SubCategory> getSubCategory(@PathVariable Long id) {
        log.debug("REST request to get SubCategory : {}", id);
        Optional<SubCategory> subCategory = subCategoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(subCategory);
    }

    /**
     * {@code DELETE  /sub-categories/:id} : delete the "id" subCategory.
     *
     * @param id the id of the subCategory to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sub-categories/{id}")
    public ResponseEntity<Void> deleteSubCategory(@PathVariable Long id) {
        log.debug("REST request to delete SubCategory : {}", id);
        subCategoryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
