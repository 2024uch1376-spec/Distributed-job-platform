package com.companyms.Company_Micro.company;

import com.companyms.Company_Micro.dto.ReviewMessage;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public boolean updateCompany(Long id, Company updatedCompany) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();
            company.setName(updatedCompany.getName());
            company.setDescription(updatedCompany.getDescription());
            companyRepository.save(company);
            return true;
        }
        return false;
    }
    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        System.out.println("Processing review for company ID: " + reviewMessage.getCompanyId());

        Company company = companyRepository.findById(reviewMessage.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + reviewMessage.getCompanyId()));

        // Update rating (or average rating)
        company.setRating(reviewMessage.getRating());
        companyRepository.save(company);
    }

    @Override
    public boolean deleteCompanyById(Long id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }
}