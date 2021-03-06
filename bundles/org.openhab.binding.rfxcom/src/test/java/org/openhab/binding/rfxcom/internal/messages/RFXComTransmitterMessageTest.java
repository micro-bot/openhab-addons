/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.rfxcom.internal.messages;

import static org.junit.Assert.assertEquals;
import static org.openhab.binding.rfxcom.internal.messages.RFXComTransmitterMessage.Response.ACK;
import static org.openhab.binding.rfxcom.internal.messages.RFXComTransmitterMessage.SubType.RESPONSE;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.core.util.HexUtils;
import org.junit.Test;
import org.openhab.binding.rfxcom.internal.exceptions.RFXComException;
import org.openhab.binding.rfxcom.internal.messages.RFXComTransmitterMessage.Response;
import org.openhab.binding.rfxcom.internal.messages.RFXComTransmitterMessage.SubType;

/**
 * Test for RFXCom-binding
 *
 * @author Martin van Wingerden - Initial contribution
 */
@NonNullByDefault
public class RFXComTransmitterMessageTest {
    private void testMessage(String hexMsg, Response response, SubType subType, int seqNbr) throws RFXComException {
        final RFXComTransmitterMessage msg = (RFXComTransmitterMessage) RFXComMessageFactory
                .createMessage(HexUtils.hexToBytes(hexMsg));
        assertEquals("SubType", subType, msg.subType);
        assertEquals("Response", response, msg.response);
        assertEquals("Seq Number", seqNbr, (short) (msg.seqNbr & 0xFF));

        byte[] decoded = msg.decodeMessage();

        assertEquals("Message converted back", hexMsg, HexUtils.bytesToHex(decoded));
    }

    @Test
    public void testSomeMessages() throws RFXComException {
        testMessage("0402014300", ACK, RESPONSE, 67);
    }
}
