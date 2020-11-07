package com.eburg_soft.mynews.presentation.mappers

import com.eburg_soft.mynews.core.BaseMapper
import com.eburg_soft.mynews.data.datasource.network.models.IpResponse
import com.eburg_soft.mynews.presentation.models.IpUiModel

class IpToUiMapper : BaseMapper<IpResponse, IpUiModel> {

    override fun map(type: IpResponse?): IpUiModel = IpUiModel(type?.countryCode)
}