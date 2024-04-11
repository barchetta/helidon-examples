/*
 * Copyright (c) 2019, 2020 Oracle and/or its affiliates. All rights reserved.
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

package io.helidon.microprofile.grpc.example.metrics;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;

import io.helidon.grpc.server.CollectingObserver;
import io.helidon.microprofile.grpc.core.Bidirectional;
import io.helidon.microprofile.grpc.core.ClientStreaming;
import io.helidon.microprofile.grpc.core.Grpc;
import io.helidon.microprofile.grpc.core.ServerStreaming;
import io.helidon.microprofile.grpc.core.Unary;

import io.grpc.stub.StreamObserver;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;

/**
 * The gRPC StringService implementation.
 * <p>
 * This class is a gRPC service annotated with {@link io.helidon.microprofile.grpc.core.Grpc}
 * and {@link javax.enterprise.context.ApplicationScoped} so that it will be discovered and deployed
 * using CDI when the MP gRPC server starts.
 */
@Grpc
@ApplicationScoped
public class StringService {

    /**
     * Convert a string value to upper case.
     * <p>
     * This method is annotated with {@literal @}{@link Timed} so a
     * gRPC metrics {@link io.grpc.ServerInterceptor} will be added
     * by the gRPC metrics CDI extension.
     *
     * @param request  the request containing the string to convert
     * @return the request value converted to upper case
     */
    @Unary
    @Timed
    public String upper(String request) {
        return request.toUpperCase();
    }

    /**
     * Convert a string value to lower case.
     * <p>
     * This method is annotated with {@literal @}{@link Timed} so a
     * gRPC metrics {@link io.grpc.ServerInterceptor} will be added
     * by the gRPC metrics CDI extension.
     *
     * @param request  the request containing the string to convert
     * @return  the request converted to lower case
     */
    @Unary
    @Timed
    public String lower(String request) {
        return request.toLowerCase();
    }

    /**
     * Split a space delimited string value and stream back the split parts.
     * @param request  the request containing the string to split
     * @return  a {@link java.util.stream.Stream} containing the split parts
     * <p>
     * This method is annotated with {@literal @}{@link Metered} so a
     * gRPC metrics {@link io.grpc.ServerInterceptor} will be added
     * by the gRPC metrics CDI extension.
     */
    @ServerStreaming
    @Metered
    public Stream<String> split(String request) {
        return Stream.of(request.split(" "));
    }

    /**
     * Join a stream of string values and return the result.
     * @param observer  the request containing the string to split
     * @return  a {@link java.util.stream.Stream} containing the split parts
     * <p>
     * This method is annotated with {@literal @}{@link Counted} so a
     * gRPC metrics {@link io.grpc.ServerInterceptor} will be added
     * by the gRPC metrics CDI extension.
     */
    @ClientStreaming
    @Counted
    public StreamObserver<String> join(StreamObserver<String> observer) {
        return new CollectingObserver<>(Collectors.joining(" "), observer);
    }

    /**
     * Echo each value streamed from the client back to the client.
     * @param observer  the {@link io.grpc.stub.StreamObserver} to send responses to
     * @return  the {@link io.grpc.stub.StreamObserver} to receive requests from
     * <p>
     * This method is annotated with {@literal @}{@link Counted} so a
     * gRPC metrics {@link io.grpc.ServerInterceptor} will be added
     * by the gRPC metrics CDI extension.
     */
    @Bidirectional
    @Counted
    public StreamObserver<String> echo(StreamObserver<String> observer) {
        return new EchoObserver(observer);
    }

    /**
     * Inner StreamObserver used to echo values back to the caller.
     */
    private static class EchoObserver
            implements StreamObserver<String> {

        private final StreamObserver<String> observer;

        private EchoObserver(StreamObserver<String> observer) {
            this.observer = observer;
        }

        @Override
        public void onNext(String msg) {
            observer.onNext(msg);
        }

        @Override
        public void onError(Throwable t) {
            t.printStackTrace();
        }

        @Override
        public void onCompleted() {
            observer.onCompleted();
        }
    }
}
