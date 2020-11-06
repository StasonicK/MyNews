package com.eburg_soft.mynews.presentation.mappers

import com.eburg_soft.mynews.core.BaseMapper
import com.eburg_soft.mynews.data.datasource.network.models.IpResponse
import com.eburg_soft.mynews.presentation.models.IpUi

class IpToUiMapper:BaseMapper<IpResponse,IpUi> {

 override fun map(type: IpResponse?): IpUi = IpUi(type?.countryCode)
}