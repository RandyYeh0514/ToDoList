package com.ian.todolist.utils.serializer

import com.ian.todolist.model.Id
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bson.types.ObjectId

object ObjectIdSerializer: KSerializer<ObjectId> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("oid", PrimitiveKind.BOOLEAN)

    override fun deserialize(decoder: Decoder): ObjectId {
        val decodedStr = decoder.decodeString()
        return ObjectId(decodedStr)
    }

    override fun serialize(encoder: Encoder, value: ObjectId) {
        encoder.encodeString(value.toString())
    }
}