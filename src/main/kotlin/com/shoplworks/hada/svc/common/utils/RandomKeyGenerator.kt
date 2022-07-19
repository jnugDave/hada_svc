package com.shoplworks.hada.svc.common.utils

import org.hibernate.HibernateException
import org.hibernate.MappingException
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.Configurable
import org.hibernate.id.IdentifierGenerator
import org.hibernate.service.ServiceRegistry
import org.hibernate.type.Type
import java.io.Serializable
import java.security.SecureRandom
import java.util.*

class RandomKeyGenerator : IdentifierGenerator, Configurable {
    private fun nextKey(len: Int): String {
        val time = System.currentTimeMillis()
        val s1 = SecureRandom().nextLong()
        val s2 = SecureRandom().nextLong()
        val s3 = SecureRandom().nextLong()
        var temp = java.lang.Long.toHexString(time and s1 or s2 xor s3).uppercase(Locale.getDefault())
        for (i in temp.length until len) temp += "0"
        return if (temp.length > len) temp.substring(0, len) else temp
    }

    private var keySize = 16
    @Throws(MappingException::class)
    override fun configure(type: Type, params: Properties, serviceRegistry: ServiceRegistry) {
        keySize = try {
            params.getProperty("key_size").toInt()
        } catch (e: Exception) {
            16
        }
    }

    @Throws(HibernateException::class)
    override fun generate(session: SharedSessionContractImplementor, obj: Any): Serializable {
        return nextKey(keySize)
    }
}