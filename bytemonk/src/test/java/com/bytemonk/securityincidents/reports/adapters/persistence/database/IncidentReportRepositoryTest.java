package com.bytemonk.securityincidents.reports.adapters.persistence.database;

import com.bytemonk.securityincidents.reports.IIncidentReportRepository;
import com.bytemonk.securityincidents.reports.adapters.persistence.domain.models.Report;
import com.bytemonk.securityincidents.reports.domain.entities.Incident;
import com.bytemonk.securityincidents.reports.domain.valueobjects.Title;
import com.bytemonk.securityincidents.users.domain.entities.User;
import com.bytemonk.securityincidents.users.domain.valueobjects.Username;

import java.util.ArrayList;
import java.util.List;

public class IncidentReportRepositoryTest implements IIncidentReportRepository {
    private final List<Report> reports;

    public IncidentReportRepositoryTest() {
        reports = new ArrayList<Report>();
    }

    @Override
    public Incident findByTitle(Title aTitle, Username username) {
        var anIncident = reports.stream()
                .filter(report -> report.getTitle().equals(aTitle.value()) && report.getOwner().equals(username))
                .findFirst();

        if (anIncident.isEmpty())
            return null;

        var aReport = anIncident.get();

        return Report.createDomain(aReport);
    }

    @Override
    public Incident saveIncident(Incident aIncident, User aUser) {
        var aReport = Report.create(aIncident, aUser);

        reports.add(aReport);

        return Report.createDomain(aReport);

    }
}
