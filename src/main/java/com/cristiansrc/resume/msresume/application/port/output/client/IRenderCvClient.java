package com.cristiansrc.resume.msresume.application.port.output.client;

import com.cristiansrc.resume.msresume.infrastructure.client.rendercv.in.CustomerCvIn;
import com.cristiansrc.resume.msresume.infrastructure.client.rendercv.out.CustomerCvOut;

public interface IRenderCvClient {
    /**
     * Envía los datos del CV al servicio de renderizado para generar el PDF.
     * @param customerCvIn Datos de entrada para el renderizado.
     * @return CustomerCvOut Respuesta del servicio con la URL o datos del PDF.
     */
    CustomerCvOut renderCv(CustomerCvIn customerCvIn);
}
