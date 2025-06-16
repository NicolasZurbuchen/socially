package ch.nicolaszurbuchen.socially.common.auth.domain.use_case

import ch.nicolaszurbuchen.socially.common.auth.domain.AuthRepositoryFake
import org.junit.Before

abstract class AuthBaseUseCase {
    protected lateinit var fakeRepository: AuthRepositoryFake

    @Before
    fun baseSetup() {
        fakeRepository = AuthRepositoryFake()
    }
}