package fr.umlv.javainside.labOne;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Benchmark)
public class TestBenchMark {
	
	@Param({"10", "100", "1000", "10000", "100000", "1000000"})
	public int maximum;
	

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public int benchmarkLoopSum() {
		return Sums.loopSum(maximum);
	}
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public int benchmarkstreamSum() {
		return Sums.streamSum(maximum);
	}
	
	public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()

                .include(TestBenchMark.class.getSimpleName())

                .forks(1)

                .build();


        new Runner(opt).run();

    }
}
