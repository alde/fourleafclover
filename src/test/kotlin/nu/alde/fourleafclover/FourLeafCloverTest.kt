package nu.alde.fourleafclover

import nu.alde.fourleafclover.models.CloverReport
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset.offset
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.nio.file.FileSystems

class FourLeafCloverTest {
    private lateinit var clover: FourLeafClover

    @BeforeEach
    internal fun setup() {
        clover = FourLeafClover()
    }

    @Test
    fun `processing a clover xml`() {
        "/fixtures/clover.xml".asResource {
            val report = clover.process(it)
            assertThat(report).isInstanceOf(CloverReport::class.java)
            assertThat(report.project.metrics.statements).isEqualTo(8237)
            assertThat(report.project.packages).hasSize(3)
            assertThat(report.project.packages.first().files).hasSize(3)
            assertThat(report.project.packages.first().files.last().lines).hasSize(7)
        }
    }

    @Test
    fun `calculates percentages`() {
        "/fixtures/clover.xml".asResource {
            val report = clover.process(it)
            assertThat(report.project.metrics.statementsPercent()).isCloseTo(76.44, offset(0.01))
            assertThat(report.project.metrics.conditionalsPercent()).isCloseTo(70.85, offset(0.01))
            assertThat(report.project.metrics.methodsPercent()).isCloseTo(70.16, offset(0.01))
            assertThat(report.project.metrics.elementsPercent()).isCloseTo(73.70, offset(0.01))
        }
    }

    @Test
    fun `metrics on a package`() {
        "/fixtures/clover.xml".asResource {
            val report = clover.process(it)
            val pkg = report.project.packages.find { p -> p.name == "another package"}!!
            assertThat(pkg.metrics.statementsPercent()).isCloseTo(64.51, offset(0.01))
            assertThat(pkg.metrics.conditionalsPercent()).isCloseTo(0.0, offset(0.01))
            assertThat(pkg.metrics.methodsPercent()).isCloseTo(54.54, offset(0.01))
            assertThat(pkg.metrics.elementsPercent()).isCloseTo(0.0, offset(0.01))
        }
    }

    @Test
    fun `process with file path`() {
        val path = object {}.javaClass.getResource("/fixtures/clover.xml").toURI()
        val reportFile = FileSystems.getDefault().provider().getPath(path)
        val report = clover.process(reportFile)
        assertThat(report).isInstanceOf(CloverReport::class.java)
        assertThat(report.project.metrics.statements).isEqualTo(8237)
    }

    fun String.asResource(work: (String) -> Unit) {
        work(object {}.javaClass.getResource(this).readText())
    }
}
