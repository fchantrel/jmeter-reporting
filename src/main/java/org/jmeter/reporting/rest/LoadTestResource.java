package org.jmeter.reporting.rest;

import org.jmeter.reporting.domain.LoadTest;
import org.jmeter.reporting.service.LoadTestService;

import restx.annotations.GET;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;

import com.google.common.base.Optional;

@Component
@RestxResource
@PermitAll
public class LoadTestResource {

	private final LoadTestService loadTestService;

	public LoadTestResource(LoadTestService loadTestService) {
		this.loadTestService = loadTestService;
	}

	@GET("/load_tests")
	public Iterable<LoadTest> find(Optional<Integer> skip,
			Optional<Integer> limit) {
		return loadTestService.find(skip, limit);
	}

	@GET("/load_tests/{name}/{version}/{run}")
	public Optional<LoadTest> findByKey(String name, String version,
			Integer run) {
		return loadTestService.findByKey(name, version, run);
	}

	@GET("/start/{name}/{version}")
	public LoadTest start(String name, String version) {
		int run = 1;

		// Get last run number
		Optional<LoadTest> ldTest = loadTestService.findLastByNameAndVersion(
				name, version);
		if (ldTest.isPresent()) {
			run = ldTest.get().getLoadTestKey().getRun() + 1;
		}

		// Create and save
		return loadTestService.createNew(name, version, run);
	}

	@GET("/stop/{name}/{version}/{run}")
	public LoadTest stop(String name, String version, Integer run) {
		return null;
	}

}
