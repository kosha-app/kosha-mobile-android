package com.musica.common.service.volley

import com.musica.common.service.models.response.ResponseType
import com.musica.common.service.models.response.ServiceResponse

class ServiceResult<T>(
    var serviceResponse: ServiceResponse = ServiceResponse(),
    var data: T? = null
)