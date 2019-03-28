package org.liberalist.website.secrets

class SecretsImpl(
        override val awsAccessKeyId: String,
        override val awsSecretKey: String
) : Secrets
