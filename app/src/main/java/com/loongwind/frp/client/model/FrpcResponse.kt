package com.loongwind.frp.client.model

data class FrpcResponse(val tcp:List<TcpItem>)

data class TcpItem(
    val name: String,
    val local_addr: String,
    val remote_addr: String,
    val type: String,
    val status: String,
    val err: String,
    val plugin: String
)