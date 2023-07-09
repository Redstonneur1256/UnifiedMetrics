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

package dev.cubxity.plugins.metrics.mindustry.bootstrap

import arc.ApplicationListener
import arc.Core
import dev.cubxity.plugins.metrics.api.logging.Logger
import dev.cubxity.plugins.metrics.api.platform.PlatformType
import dev.cubxity.plugins.metrics.common.UnifiedMetricsBootstrap
import dev.cubxity.plugins.metrics.common.plugin.dispatcher.CurrentThreadDispatcher
import dev.cubxity.plugins.metrics.mindustry.UnifiedMetricsMindustryPlugin
import dev.cubxity.plugins.metrics.mindustry.util.MindustryLogger
import kotlinx.coroutines.CoroutineDispatcher
import mindustry.Vars
import mindustry.core.Version
import mindustry.mod.Plugin
import java.nio.file.Path

class UnifiedMetricsMindustryBootstrap : Plugin(), UnifiedMetricsBootstrap {

    private val plugin = UnifiedMetricsMindustryPlugin(this)

    override fun init() {
        plugin.enable()

        Core.app.addListener(
            object: ApplicationListener {
                override fun dispose() {
                    plugin.disable()
                }
            },
        )
    }

    override val type: PlatformType
        get() = PlatformType.Mindustry

    override val version: String
        get() = Vars.mods.getMod(javaClass).meta.version

    override val serverBrand: String
        get() = Version.type

    override val dataDirectory: Path
        get() = configDirectory.parent

    override val configDirectory: Path
        get() = config.parent().file().toPath()

    override val logger: Logger = MindustryLogger()

    override val dispatcher: CoroutineDispatcher = CurrentThreadDispatcher

}