/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.test.kotlintest

import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.kotlintest.MicronautKotlinTestExtension.getMock
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

@MicronautTest
class MathServiceTestSimilarNameTest(private val mathService: MathService): BehaviorSpec({

    given("test similarly named test suites dont leak mocks") {

        `when`("the mock is provided") {
            val mock = getMock(mathService)
            every { mock.compute(10) } returns 20

            then("the mock is used") {
                mock.compute(10) shouldBe 20
                verify { mock.compute(10) }
            }
        }
    }

}) {

    @MockBean(MathServiceImpl::class)
    fun mathService(): MathService {
        return mockk()
    }
}
