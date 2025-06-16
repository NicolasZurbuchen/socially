package ch.nicolaszurbuchen.socially.timeline.domain.use_case

import ch.nicolaszurbuchen.socially.timeline.domain.TimelineRepositoryFake
import org.junit.Before

abstract class TimelineBaseUseCase {
    protected lateinit var fakeRepository: TimelineRepositoryFake

    @Before
    fun baseSetup() {
        fakeRepository = TimelineRepositoryFake()
    }
}