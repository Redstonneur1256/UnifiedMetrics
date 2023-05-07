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

package dev.cubxity.plugins.metrics.mindustry.metric.tick

import arc.Core
import dev.cubxity.plugins.metrics.api.metric.collector.Collector
import dev.cubxity.plugins.metrics.api.metric.collector.CollectorCollection
import dev.cubxity.plugins.metrics.api.metric.collector.Histogram
import fr.redstonneur1256.modlib.event.EventUtil
import fr.redstonneur1256.modlib.event.RegisteredListener
import mindustry.game.EventType

class TickCollection : CollectorCollection {

    private val histogram = Histogram("mindustry_tick_delta")
    private var listener: RegisteredListener? = null

    override val collectors: List<Collector>
        get() = listOf(histogram, TickCollector())

    override fun initialize() {
        listener = EventUtil.run(EventType.Trigger.update) { histogram.plusAssign(Core.graphics.deltaTime.toDouble()) }
    }

    override fun dispose() {
        listener!!.unregister()
    }

}