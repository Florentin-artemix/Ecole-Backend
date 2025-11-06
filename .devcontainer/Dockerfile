# Use Microsoft's Java 21 dev container image
FROM mcr.microsoft.com/devcontainers/java:21

# Install additional tools
RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
    && apt-get -y install --no-install-recommends \
    postgresql-client \
    maven \
    && apt-get clean -y && rm -rf /var/lib/apt/lists/*

# Set the default shell to bash rather than sh
ENV SHELL=/bin/bash

# Set working directory
WORKDIR /workspace

# Configure Maven
ENV MAVEN_CONFIG=/root/.m2
