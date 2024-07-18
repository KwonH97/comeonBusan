package com.example.comeonBusan.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;

import com.example.comeonBusan.entity.Lodgment;
import com.example.comeonBusan.repository.LodgmentRepository;

import jakarta.annotation.PostConstruct;

@Service
public class LodgmentService {

	@Autowired
	private LodgmentRepository lodgmentRepository;
	
	@Autowired
	private DataSource dataSource;

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public List<Lodgment> getLodgmentList() {

		return lodgmentRepository.findAll();
	}

	public Page<Lodgment> LodgmentPage(Pageable pageable) {
		return lodgmentRepository.findAll(pageable);
	}

	public Lodgment saveLodgment(Lodgment lodgment) {
		return lodgmentRepository.save(lodgment);
	}

	public Lodgment getLodgmentById(Long lid) {
		Optional<Lodgment> lodgment = lodgmentRepository.findById(lid);
		return lodgment.orElse(null);
	}

	public boolean deleteLodgment(Long lid) {
		if (lodgmentRepository.existsById(lid)) {
			lodgmentRepository.deleteById(lid);
			return true;
		}
		return false;
	}
	
	public List<Lodgment> getLodgmentsByName(String 업체명) {
        return lodgmentRepository.findBy업체명(업체명);
    }
	
	@PostConstruct
    public void init() {
        if (!isDataPresent()) {
            executeSqlScript();
        }
    }

    public void executeSqlScript() {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("static/db/comeonB.sql"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isDataPresent() {
        String sql = "SELECT COUNT(*) FROM lodgment";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null && count > 0;
    }
}
