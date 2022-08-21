package com.loongwind.frp.client.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany


@Entity
data class IniProperty(@Id var id:Long = 0, var key:String, var value:String)

@Entity
data class IniSection(
    @Id var id: Long = 0,
    var name: String){
    lateinit var configs : ToMany<IniProperty>
}


@Entity
data class IniConfig(
    @Id var id:Long = 0,
    var name:String = ""
){
    lateinit var sections: ToMany<IniSection>
}



