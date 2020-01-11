package com.massa.category.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "subCategory")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SubCategory> categories = new HashSet<>();

    @OneToOne(mappedBy = "subCategory")
    @JsonIgnore
    private SubCategory category;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Category name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Category description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<SubCategory> getCategories() {
        return categories;
    }

    public Category categories(Set<SubCategory> subCategories) {
        this.categories = subCategories;
        return this;
    }

    public Category addCategory(SubCategory subCategory) {
        this.categories.add(subCategory);
        subCategory.setSubCategory(this);
        return this;
    }

    public Category removeCategory(SubCategory subCategory) {
        this.categories.remove(subCategory);
        subCategory.setSubCategory(null);
        return this;
    }

    public void setCategories(Set<SubCategory> subCategories) {
        this.categories = subCategories;
    }

    public SubCategory getCategory() {
        return category;
    }

    public Category category(SubCategory subCategory) {
        this.category = subCategory;
        return this;
    }

    public void setCategory(SubCategory subCategory) {
        this.category = subCategory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
