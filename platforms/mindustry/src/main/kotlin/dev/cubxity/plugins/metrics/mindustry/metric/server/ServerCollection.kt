/*
 *     This file is part of UnifiedMetrics.
 *
 *     UnifiedMetrics is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     UnifiedMetrics is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with UnifiedMetrics.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.cubxity.plugins.metrics.mindustry.metric.server

import dev.cubxity.plugins.metrics.api.metric.collector.Collector
import dev.cubxity.plugins.metrics.api.metric.collector.CollectorCollection
import dev.cubxity.plugins.metrics.api.metric.collector.Counter
import dev.cubxity.plugins.metrics.api.metric.store.DoubleAdderStore
import fr.redstonneur1256.modlib.event.EventUtil
import fr.redstonneur1256.modlib.event.RegisteredListener
import fr.redstonneur1256.modlib.events.net.server.ServerListPingEvent

class ServerCollection : CollectorCollection {

    private val pingCounter = Counter("mindustry_server_ping_total", valueStoreFactory = DoubleAdderStore)
    private var listener: RegisteredListener? = null

    override val collectors: List<Collector>
        get() = listOf(ServerCollector(), pingCounter)

    override fun initialize() {
        listener = EventUtil.run(
            ServerListPingEvent::class.java
        ) { pingCounter.inc() }
    }

    override fun dispose() {
        listener!!.unregister()
    }

}
