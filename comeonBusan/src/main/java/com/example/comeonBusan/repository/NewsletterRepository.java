package com.example.comeonBusan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.comeonBusan.entity.Newsletter;

public interface NewsletterRepository extends JpaRepository<Newsletter, Long> {
}
