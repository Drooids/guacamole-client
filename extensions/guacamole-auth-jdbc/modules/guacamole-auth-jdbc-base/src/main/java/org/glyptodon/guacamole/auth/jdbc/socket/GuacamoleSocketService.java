/*
 * Copyright (C) 2015 Glyptodon LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.glyptodon.guacamole.auth.jdbc.socket;

import java.util.Collection;
import org.glyptodon.guacamole.auth.jdbc.user.AuthenticatedUser;
import org.glyptodon.guacamole.auth.jdbc.connection.ModeledConnection;
import org.glyptodon.guacamole.auth.jdbc.connectiongroup.ModeledConnectionGroup;
import org.glyptodon.guacamole.GuacamoleException;
import org.glyptodon.guacamole.net.GuacamoleSocket;
import org.glyptodon.guacamole.net.auth.Connection;
import org.glyptodon.guacamole.net.auth.ConnectionGroup;
import org.glyptodon.guacamole.net.auth.ConnectionRecord;
import org.glyptodon.guacamole.protocol.GuacamoleClientInformation;


/**
 * Service which creates pre-configured GuacamoleSocket instances for
 * connections and balancing groups, applying concurrent usage rules.
 *
 * @author Michael Jumper
 */
public interface GuacamoleSocketService {

    /**
     * Creates a socket for the given user which connects to the given
     * connection. The given client information will be passed to guacd when
     * the connection is established. This function will apply any concurrent
     * usage rules in effect, but will NOT test object- or system-level
     * permissions.
     *
     * @param user
     *     The user for whom the connection is being established.
     *
     * @param connection
     *     The connection the user is connecting to.
     *
     * @param info
     *     Information describing the Guacamole client connecting to the given
     *     connection.
     *
     * @return
     *     A new GuacamoleSocket which is configured and connected to the given
     *     connection.
     *
     * @throws GuacamoleException
     *     If the connection cannot be established due to concurrent usage
     *     rules.
     */
    GuacamoleSocket getGuacamoleSocket(AuthenticatedUser user,
            ModeledConnection connection, GuacamoleClientInformation info)
            throws GuacamoleException;

    /**
     * Returns a connection containing connection records representing all
     * currently-active connections using the given connection. These records
     * will have usernames and start dates, but no end date, and will be
     * sorted in ascending order by start date.
     *
     * @param connection
     *     The connection to check.
     *
     * @return
     *     A connection containing connection records representing all
     *     currently-active connections.
     */
    public Collection<ConnectionRecord> getActiveConnections(Connection connection);

    /**
     * Creates a socket for the given user which connects to the given
     * connection group. The given client information will be passed to guacd
     * when the connection is established. This function will apply any
     * concurrent usage rules in effect, but will NOT test object- or
     * system-level permissions.
     *
     * @param user
     *     The user for whom the connection is being established.
     *
     * @param connectionGroup
     *     The connection group the user is connecting to.
     *
     * @param info
     *     Information describing the Guacamole client connecting to the given
     *     connection group.
     *
     * @return
     *     A new GuacamoleSocket which is configured and connected to the given
     *     connection group.
     *
     * @throws GuacamoleException
     *     If the connection cannot be established due to concurrent usage
     *     rules, or if the connection group is not balancing.
     */
    GuacamoleSocket getGuacamoleSocket(AuthenticatedUser user,
            ModeledConnectionGroup connectionGroup,
            GuacamoleClientInformation info)
            throws GuacamoleException;

    /**
     * Returns a collection containing connection records representing all
     * currently-active connections using the given connection group. These
     * records will have usernames and start dates, but no end date, and will
     * be sorted in ascending order by start date.
     *
     * @param connectionGroup
     *     The connection group to check.
     *
     * @return
     *     A collection containing connection records representing all
     *     currently-active connections.
     */
    public Collection<ConnectionRecord> getActiveConnections(ConnectionGroup connectionGroup);

}
