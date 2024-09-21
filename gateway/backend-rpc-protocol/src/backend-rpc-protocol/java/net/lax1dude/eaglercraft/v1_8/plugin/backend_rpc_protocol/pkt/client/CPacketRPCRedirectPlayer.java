package net.lax1dude.eaglercraft.v1_8.plugin.backend_rpc_protocol.pkt.client;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import net.lax1dude.eaglercraft.v1_8.plugin.backend_rpc_protocol.pkt.EaglerBackendRPCHandler;
import net.lax1dude.eaglercraft.v1_8.plugin.backend_rpc_protocol.pkt.EaglerBackendRPCPacket;

/**
 * Copyright (c) 2024 lax1dude. All Rights Reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 */
public class CPacketRPCRedirectPlayer implements EaglerBackendRPCPacket {

	public String redirectURI;

	public CPacketRPCRedirectPlayer() {
	}

	public CPacketRPCRedirectPlayer(String redirectURI) {
		this.redirectURI = redirectURI;
	}

	@Override
	public void readPacket(DataInput buffer) throws IOException {
		byte[] redirectURIBytes = new byte[buffer.readUnsignedShort()];
		buffer.readFully(redirectURIBytes);
		redirectURI = new String(redirectURIBytes, StandardCharsets.US_ASCII);
	}

	@Override
	public void writePacket(DataOutput buffer) throws IOException {
		if(redirectURI.length() > 65535) {
			throw new IOException("Redirect URI is too long!");
		}
		byte[] redirectURIBytes = redirectURI.getBytes(StandardCharsets.US_ASCII);
		buffer.writeShort(redirectURIBytes.length);
		buffer.write(redirectURIBytes);
	}

	@Override
	public void handlePacket(EaglerBackendRPCHandler handler) {
		handler.handleClient(this);
	}

	@Override
	public int length() {
		return 2 + redirectURI.length();
	}

}