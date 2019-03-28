package org.liberalist.website.credentials

import com.amazonaws.auth.AWSCredentials

class Credentials(private val awsAccessKeyId: String, private val awsSecretKey: String) : AWSCredentials {
    override fun getAWSAccessKeyId(): String = awsAccessKeyId
    override fun getAWSSecretKey(): String = awsSecretKey
}
