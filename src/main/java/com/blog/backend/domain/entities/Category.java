package com.blog.backend.domain.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "categories")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

    public Category() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public Category(UUID id, String name, List<Posts> posts) {
        this.id = id;
        this.name = name;
        this.posts = posts;
    }

    @Column(nullable = false,unique = true)
	private String name;
	
	@OneToMany(mappedBy = "category")
	private List<Posts> posts = new ArrayList<>();
	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Posts> getPosts() {
        return posts;
    }

    public void setPosts(List<Posts> posts) {
        this.posts = posts;
    }

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

    public String getName() {
        return this.name;
    }
}
