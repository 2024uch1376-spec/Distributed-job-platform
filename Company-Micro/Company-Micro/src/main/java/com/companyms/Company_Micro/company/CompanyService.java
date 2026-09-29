package com.companyms.Company_Micro.company;

import com.companyms.Company_Micro.dto.ReviewMessage;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    Company getCompanyById(Long id);
    void createCompany(Company company);
    boolean updateCompany(Long id, Company company);
    boolean deleteCompanyById(Long id);

    void updateCompanyRating(ReviewMessage reviewMessage);
}