/*
 * Copyright (c) 2024 Oracle and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.helidon.examples.microprofile.coherence;

import com.oracle.coherence.cdi.Name;
import com.tangosol.net.NamedCache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static java.lang.System.Logger.Level.INFO;

/**
 * Credit score application.
 */
@ApplicationScoped
@Path("/creditscore")
public class CreditscoreService {

    private static final System.Logger LOGGER = System.getLogger(CreditscoreService.class.getName());
    private static final String CACHE_NAME = "creditScoreCache";

    private static final int SCORE_MAX = 800;
    private static final int SCORE_MIN = 550;

    @Inject
    @Name(CACHE_NAME)
    private NamedCache<String, Integer> creditScoreCache;

    /**
     * Generate a credit score.
     * @param person Person to generate score for
     * @return Person with score populated and ssn redacted
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postMethodCreditScore(Person person) {

        if (person.firstName() == null || person.lastName() == null || person.dateOfBirth() == null || person.ssn() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Bad request").build();
        }

        LOGGER.log(INFO, "Computing credit score for " + person.firstName() + " " + person.lastName());

        String ssn = person.ssn();
        Integer creditScore = creditScoreCache.get(ssn);

        if (creditScore == null) {
            creditScore = calculateCreditScore(person);
            creditScoreCache.put(ssn, creditScore);
        }
        return Response.ok(
                new Person(person.firstName(), person.lastName(), person.dateOfBirth(), "NNN-NN-NNNN", creditScore))
                .build();
    }

    private int calculateCreditScore(Person p) {
        int score = Math.abs(p.hashCode()) % SCORE_MAX;
        while (score < SCORE_MIN) {
            score = score + 100;
        }
        // Pause for dramatic effect
        sleep();
        return score;
    }

    private void sleep() {
        try {
            Thread.sleep(2 * 1_000L);
        } catch (InterruptedException ignored) {
        }
    }
}