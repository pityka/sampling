package sampling

import org.scalatest._

class Normal extends FunSuite with Matchers {

  test("normal mean and variance") {
    val rnd = new scala.util.Random(1)
    val variance = 1.0
    val mean = 0.5
    def unnormalized(x: Double) =
      math.exp(-1 * math.pow(x - mean, 2d) / (2 * variance))
    val samplingMin = -10d
    val samplingMax = 10d
    def sampling(x: Double) = 1d / (samplingMax - samplingMin)
    def sample() = {
      val u = rnd.nextDouble
      if (u <= 0) samplingMin
      else if (u >= 1.0) samplingMax
      else u * (samplingMax - samplingMin) + samplingMin
    }
    val mean_estimate =
      ImportanceSampling.unnormalized(
        evaluateUnnormalizedTarget = unnormalized _,
        sampleFromSamplingDistribution = sample,
        evaluateSamplingDistribution = sampling _,
        f = (x: Double) => x,
        numberOfSamples = 100000
      )
    val variance_estimate =
      ImportanceSampling.unnormalized(
        evaluateUnnormalizedTarget = unnormalized _,
        sampleFromSamplingDistribution = sample,
        evaluateSamplingDistribution = sampling _,
        f = (x: Double) => (x - mean) * (x - mean),
        numberOfSamples = 100000
      )

    mean_estimate.estimate shouldBe 0.4999675832045339
    variance_estimate.estimate shouldBe 1.000199909018502

  }

  test("beta mean and variance") {
    val rnd = new scala.util.Random(1)
    val alpha = 1.0
    val beta = 2.0
    def unnormalized(x: Double) =
      math.pow(x, alpha - 1) * math.pow(1 - x, beta - 1)
    val samplingMin = 0d
    val samplingMax = 1d
    def sampling(x: Double) = 1d / (samplingMax - samplingMin)
    def sample() = {
      val u = rnd.nextDouble
      if (u <= 0) samplingMin
      else if (u >= 1.0) samplingMax
      else u * (samplingMax - samplingMin) + samplingMin
    }
    val mean_estimate =
      ImportanceSampling.unnormalized(
        evaluateUnnormalizedTarget = unnormalized _,
        sampleFromSamplingDistribution = sample,
        evaluateSamplingDistribution = sampling _,
        f = (x: Double) => x,
        numberOfSamples = 10000
      )

    mean_estimate.estimate shouldBe 0.3350847698742026

    val variance_estimate =
      ImportanceSampling.unnormalized(
        evaluateUnnormalizedTarget = unnormalized _,
        sampleFromSamplingDistribution = sample,
        evaluateSamplingDistribution = sampling _,
        f = (x: Double) =>
          (x - alpha / (alpha + beta)) * (x - alpha / (alpha + beta)),
        numberOfSamples = 100000
      )
    variance_estimate.estimate shouldBe 0.05559011480321028
  }
}
