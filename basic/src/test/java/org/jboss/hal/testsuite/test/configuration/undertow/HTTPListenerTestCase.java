package org.jboss.hal.testsuite.test.configuration.undertow;

import org.apache.commons.lang.RandomStringUtils;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.hal.testsuite.category.Shared;
import org.jboss.hal.testsuite.creaper.ResourceVerifier;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.formeditor.Editor;
import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;
import org.jboss.hal.testsuite.page.config.UndertowHTTPPage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.wildfly.extras.creaper.commands.undertow.AddUndertowListener;
import org.wildfly.extras.creaper.core.CommandFailedException;
import org.wildfly.extras.creaper.core.online.operations.Address;
import org.wildfly.extras.creaper.core.online.operations.OperationException;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RunWith(Arquillian.class)
@Category(Shared.class)
public class HTTPListenerTestCase extends UndertowTestCaseAbstract {

    @Page
    private UndertowHTTPPage page;

    //identifiers
    private static final String ALLOW_ENCODED_SLASH = "allow-encoded-slash";
    private static final String ALLOW_EQUALS_IN_COOKIE_VALUE = "allow-equals-in-cookie-value";
    private static final String ALWAYS_SET_KEEP_ALIVE = "always-set-keep-alive";
    private static final String BUFFER_PIPELINED_DATA = "buffer-pipelined-data";
    private static final String BUFFER_POOL = "buffer-pool";
    private static final String CERTIFICATE_FORWARDING = "certificate-forwarding";
    private static final String DECODE_URL = "decode-url";
    private static final String ENABLE_HTTP2 = "enable-http2";
    private static final String ENABLED = "enabled";
    private static final String MAX_BUFFERED_REQUEST_SIZE = "max-buffered-request-size";
    private static final String MAX_CONNECTIONS = "max-connections";
    private static final String MAX_COOKIES = "max-cookies";
    private static final String MAX_HEADER_SIZE = "max-header-size";
    private static final String MAX_HEADERS = "max-headers";
    private static final String MAX_PARAMETERS = "max-parameters";
    private static final String MAX_POST_SIZE = "max-post-size";
    private static final String NO_REQUEST_TIMEOUT = "no-request-timeout";
    private static final String PROXY_ADDRESS_FORWARDING = "proxy-address-forwarding";
    private static final String READ_TIMEOUT = "read-timeout";
    private static final String RECEIVE_BUFFER = "receive-buffer";
    private static final String RECORD_REQUEST_START_TIME = "record-request-start-time";
    private static final String REDIRECT_SOCKET = "redirect-socket";
    private static final String REQUEST_PARSE_TIMEOUT = "request-parse-timeout";
    private static final String RESOLVE_PEER_ADDRESS = "resolve-peer-address";
    private static final String SEND_BUFFER = "send-buffer";
    private static final String SOCKET_BINDING = "socket-binding";
    private static final String TCP_BACKLOG = "tcp-backlog";
    private static final String TCP_KEEP_ALIVE = "tcp-keep-alive";
    private static final String URL_CHARSET = "url-charset";
    private static final String WORKER = "worker";
    private static final String WRITE_TIMEOUT = "write-timeout";

    //values
    private static final String HTTP_SERVER = "undertow-http-server-http-listener_" + RandomStringUtils.randomAlphanumeric(5);

    private static final String HTTP_LISTENER = "http-listener_" + RandomStringUtils.randomAlphanumeric(5);
    private static final String HTTP_LISTENER_TBR = "http-listener-tbr_" + RandomStringUtils.randomAlphanumeric(5);
    private static final String HTTP_LISTENER_TBA = "http-listener-tba_" + RandomStringUtils.randomAlphanumeric(5);

    private static final Address HTTP_SERVER_ADDRESS = UNDERTOW_ADDRESS.and("server", HTTP_SERVER);

    private static final Address HTTP_LISTENER_ADDRESS = HTTP_SERVER_ADDRESS.and("http-listener", HTTP_LISTENER);
    private static final Address HTTP_LISTENER_TBR_ADDRESS = HTTP_SERVER_ADDRESS.and("http-listener", HTTP_LISTENER_TBR);
    private static final Address HTTP_LISTENER_TBA_ADDRESS = HTTP_SERVER_ADDRESS.and("http-listener", HTTP_LISTENER_TBA);

    @BeforeClass
    public static void setUp() throws IOException, CommandFailedException, TimeoutException, InterruptedException {
        operations.add(HTTP_SERVER_ADDRESS);
        administration.reloadIfRequired();
        client.apply(new AddUndertowListener
                .HttpBuilder(HTTP_LISTENER, HTTP_SERVER, undertowOps.createSocketBinding()).build());
        client.apply(new AddUndertowListener
                .HttpBuilder(HTTP_LISTENER_TBR, HTTP_SERVER, undertowOps.createSocketBinding()).build());
        administration.reloadIfRequired();
    }

    @Before
    public void before() {
        page.navigate();
        page.viewHTTPServer(HTTP_SERVER)
                .switchToHTTPListeners()
                .selectItemInTableByText(HTTP_LISTENER);
    }

    @AfterClass
    public static void tearDown() throws InterruptedException, CommandFailedException, TimeoutException, IOException, OperationException {
        operations.removeIfExists(HTTP_LISTENER_ADDRESS);
        operations.removeIfExists(HTTP_LISTENER_TBA_ADDRESS);
        operations.removeIfExists(HTTP_LISTENER_TBR_ADDRESS);
        operations.removeIfExists(HTTP_SERVER_ADDRESS);
    }

    @Test
    public void setAllowEncodedSlashToTrue() throws Exception {
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, ALLOW_ENCODED_SLASH, true);
    }

    @Test
    public void setAllowEncodedSlashToFalse() throws Exception {
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, ALLOW_ENCODED_SLASH, false);
    }

    @Test
    public void setAllowEqualsInCookieValueToTrue() throws Exception {
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, ALLOW_EQUALS_IN_COOKIE_VALUE, true);
    }

    @Test
    public void setAllowEqualsInCookieValueToFalse() throws Exception {
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, ALLOW_EQUALS_IN_COOKIE_VALUE, false);
    }

    @Test
    public void setAlwaysSetKeepAliveToTrue() throws Exception {
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, ALWAYS_SET_KEEP_ALIVE, true);
    }

    @Test
    public void setAlwaysSetKeepAliveToFalse() throws Exception {
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, ALWAYS_SET_KEEP_ALIVE, false);
    }

    @Test
    public void setBufferPipelinedDataToTrue() throws Exception {
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, BUFFER_PIPELINED_DATA, true);
    }

    @Test
    public void setBufferPipelinedDataToFalse() throws Exception {
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, BUFFER_PIPELINED_DATA, false);
    }

    @Test
    public void editBufferPool() throws Exception {
        editTextAndVerify(HTTP_LISTENER_ADDRESS, BUFFER_POOL, undertowOps.createBufferPool());
    }

    @Test
    public void setCertificateForwardingToTrue() throws Exception {
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, CERTIFICATE_FORWARDING, true);
    }

    @Test
    public void setCertificateForwardingToFalse() throws Exception {
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, CERTIFICATE_FORWARDING, false);
    }

    @Test
    public void setDecodeURLToTrue() throws Exception {
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, DECODE_URL, true);
    }

    @Test
    public void setDecodeURLToFalse() throws Exception {
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, DECODE_URL, false);
    }

    //TODO:DISALLOWED METHODS

    @Test
    public void setEnableHTTP2ToTrue() throws Exception {
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, ENABLE_HTTP2, true);
    }

    @Test
    public void setEnableHTTP2ToFalse() throws Exception {
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, ENABLE_HTTP2, false);
    }

    @Test
    public void setEnabledToTrue() throws Exception {
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, ENABLED, true);
    }

    @Test
    public void setEnabledToFalse() throws Exception {
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, ENABLED, false);
    }

    @Test
    public void editMaxBufferedRequestSize() throws Exception {
        editTextAndVerify(HTTP_LISTENER_ADDRESS, MAX_BUFFERED_REQUEST_SIZE, NUMERIC_VALID);
    }

    @Test
    public void editMaxBufferedRequestSizeInvalid() {
        verifyIfErrorAppears(MAX_BUFFERED_REQUEST_SIZE, NUMERIC_INVALID);
    }

    @Test
    public void editMaxConnections() throws Exception {
        editTextAndVerify(HTTP_LISTENER_ADDRESS, MAX_CONNECTIONS, NUMERIC_VALID);
    }

    @Test
    public void editMaxConnectionsInvalid() {
        verifyIfErrorAppears(MAX_CONNECTIONS, NUMERIC_INVALID);
    }

    @Test
    public void editMaxCookies() throws Exception {
        editTextAndVerify(HTTP_LISTENER_ADDRESS, MAX_COOKIES, NUMERIC_VALID);
    }

    @Test
    public void editMaxCookiesInvalid() {
        verifyIfErrorAppears(MAX_COOKIES, NUMERIC_INVALID);
    }

    @Test
    public void editMaxHeaderSize() throws Exception {
        editTextAndVerify(HTTP_LISTENER_ADDRESS, MAX_HEADER_SIZE, NUMERIC_VALID);
    }

    @Test
    public void editMaxHeaderSizeInvalid() {
        verifyIfErrorAppears(MAX_HEADER_SIZE, NUMERIC_INVALID);
    }

    @Test
    public void editMaxHeaders() throws Exception {
        editTextAndVerify(HTTP_LISTENER_ADDRESS, MAX_HEADERS, NUMERIC_VALID);
    }

    @Test
    public void editMaxHeadersInvalid() {
        verifyIfErrorAppears(MAX_HEADERS, NUMERIC_INVALID);
    }

    @Test
    public void editMaxParameters() throws Exception {
        editTextAndVerify(HTTP_LISTENER_ADDRESS, MAX_PARAMETERS, NUMERIC_VALID);
    }

    @Test
    public void editMaxParametersInvalid() {
        verifyIfErrorAppears(MAX_PARAMETERS, NUMERIC_INVALID);
    }

    @Test
    public void editMaxPostSize() throws Exception {
        editTextAndVerify(HTTP_LISTENER_ADDRESS, MAX_POST_SIZE, NUMERIC_VALID_LONG);
    }

    @Test
    public void editMaxPostSizeInvalid() {
        verifyIfErrorAppears(MAX_POST_SIZE, NUMERIC_INVALID);
    }

    @Test
    public void editNoRequestTimeout() throws Exception {
        editTextAndVerify(HTTP_LISTENER_ADDRESS, NO_REQUEST_TIMEOUT, NUMERIC_VALID);
    }

    @Test
    public void editNoRequestInvalid() {
        verifyIfErrorAppears(NO_REQUEST_TIMEOUT, NUMERIC_INVALID);
    }

    @Test
    public void setProxyAddressForwardingToTrue() throws Exception {
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, PROXY_ADDRESS_FORWARDING, true);
    }

    @Test
    public void setProxyAddressForwardingToFalse() throws Exception {
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, PROXY_ADDRESS_FORWARDING, false);
    }

    @Test
    public void editReadTimeout() throws Exception {
        editTextAndVerify(HTTP_LISTENER_ADDRESS, READ_TIMEOUT, NUMERIC_VALID);
    }

    @Test
    public void editReadTimeoutInvalid() throws Exception {
        verifyIfErrorAppears(READ_TIMEOUT, NUMERIC_INVALID);
    }

    @Test
    public void editReceiveBuffer() throws Exception {
        editTextAndVerify(HTTP_LISTENER_ADDRESS, RECEIVE_BUFFER, NUMERIC_VALID);
    }

    @Test
    public void editReceiveBufferInvalid() {
        verifyIfErrorAppears(RECEIVE_BUFFER, NUMERIC_INVALID);
    }

    @Test
    public void setRecordRequestStartTimeToTrue() throws Exception {
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, RECORD_REQUEST_START_TIME, true);
    }

    @Test
    public void setRecordRequestStartTimeToFalse() throws Exception {
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, RECORD_REQUEST_START_TIME, false);
    }

    @Test
    public void editRedirectSocket() throws Exception {
        editTextAndVerify(HTTP_LISTENER_ADDRESS, REDIRECT_SOCKET, undertowOps.createSocketBinding());
    }

    @Test
    public void editRequestParseTimeout() throws Exception {
        editTextAndVerify(HTTP_LISTENER_ADDRESS, REQUEST_PARSE_TIMEOUT, NUMERIC_VALID);
    }

    @Test
    public void editRequestParseTimeoutInvalid() throws Exception {
        verifyIfErrorAppears(REQUEST_PARSE_TIMEOUT, NUMERIC_INVALID);
    }

    @Test
    public void setResolvePeerAddressToTrue() throws Exception {
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, RESOLVE_PEER_ADDRESS, true);
    }

    @Test
    public void setResolvePeerAddressToFalse() throws Exception {
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, RESOLVE_PEER_ADDRESS, false);
    }

    @Test
    public void editSendBuffer() throws Exception {
        editTextAndVerify(HTTP_LISTENER_ADDRESS, SEND_BUFFER, NUMERIC_VALID);
    }

    @Test
    public void editSendBufferInvalid() throws Exception {
        verifyIfErrorAppears(SEND_BUFFER, NUMERIC_INVALID);
    }

    @Test
    public void editSocketBinding() throws Exception {
        editTextAndVerify(HTTP_LISTENER_ADDRESS, SOCKET_BINDING, undertowOps.createSocketBinding());
    }

    @Test
    public void editTCPBacklog() throws Exception {
        editTextAndVerify(HTTP_LISTENER_ADDRESS, TCP_BACKLOG, NUMERIC_VALID);
    }

    @Test
    public void editTCPBacklogInvalid() {
        verifyIfErrorAppears(TCP_BACKLOG, NUMERIC_INVALID);
    }

    @Test
    public void setTCPKeepAliveToTrue() throws Exception {
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, TCP_KEEP_ALIVE, true);
    }

    @Test
    public void setTCPKeepAliveToFalse() throws Exception {
        operations.writeAttribute(HTTP_LISTENER_ADDRESS, TCP_KEEP_ALIVE, true);
        administration.reloadIfRequired();
        page.navigate();
        page.viewHTTPServer(HTTP_SERVER)
                .switchToHTTPListeners()
                .selectItemInTableByText(HTTP_LISTENER);
        editCheckboxAndVerify(HTTP_LISTENER_ADDRESS, TCP_KEEP_ALIVE, false);
    }

    @Test
    public void editURLCharset() throws Exception {
        editTextAndVerify(HTTP_LISTENER_ADDRESS, URL_CHARSET);
    }

    @Test
    public void editWorker() throws Exception {
        editTextAndVerify(HTTP_LISTENER_ADDRESS, WORKER, undertowOps.createWorker());
    }

    @Test
    public void editWriteTimeout() throws Exception {
        editTextAndVerify(HTTP_LISTENER_ADDRESS, WRITE_TIMEOUT, NUMERIC_VALID);
    }

    @Test
    public void editWriteTimeoutInvalid() throws Exception {
        verifyIfErrorAppears(WRITE_TIMEOUT, NUMERIC_INVALID);
    }

    @Test
    public void addHTTPListenerInGUI() throws Exception {
        String socketBinding = undertowOps.createSocketBinding();
        ConfigFragment config = page.getConfigFragment();
        WizardWindow wizard = config.getResourceManager().addResource();

        Editor editor = wizard.getEditor();
        editor.text("name", HTTP_LISTENER_TBA);
        editor.text(SOCKET_BINDING, socketBinding);
        boolean result = wizard.finishAndDismissReloadRequiredWindow();

        Assert.assertTrue("Window should be closed", result);
        Assert.assertTrue("HTTP listener should be present in table", config.resourceIsPresent(HTTP_LISTENER_TBA));
        ResourceVerifier verifier = new ResourceVerifier(HTTP_LISTENER_TBA_ADDRESS, client);
        verifier.verifyExists();
        verifier.verifyAttribute(SOCKET_BINDING, socketBinding);
    }

    @Test
    public void removeHTTPListenerInGUI() throws Exception {
        ConfigFragment config = page.getConfigFragment();
        config.getResourceManager()
                .removeResource(HTTP_LISTENER_TBR)
                .confirmAndDismissReloadRequiredMessage();

        Assert.assertFalse("HTTP listener host should not be present in table", config.resourceIsPresent(HTTP_LISTENER_TBR));
        new ResourceVerifier(HTTP_LISTENER_TBR_ADDRESS, client).verifyDoesNotExist(); //HTTP server host should not be present on the server
    }


}
