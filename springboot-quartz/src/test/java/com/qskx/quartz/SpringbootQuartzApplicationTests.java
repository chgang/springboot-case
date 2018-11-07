package com.qskx.quartz;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class SpringbootQuartzApplicationTests {

//	@Autowired
//	private JobAndTriggerServiceImpl jobAndTriggerService;

	@Test
	public void contextLoads() {
//		PageInfo<ScheduleJob> pageInfo = jobAndTriggerService.getJobAndTriggerDetails(0,10);
//		System.out.printf("===========" + pageInfo.getList().size());

//		System.out.println(modifyTheValue2(3, val -> val + 2, val -> val + 3));

//		List<Person> list = new ArrayList();
//		list.add(new Person(1, "haha"));
//		list.add(new Person(2, "rere"));
//		list.add(new Person(3, "fefe"));
//		Map<Integer, Person> mapp = list.stream().collect(Collectors.toMap(Person::getId, Function.identity()));
//		System.out.println(mapp);

//		Set<String> set = new HashSet<>();
//		set.add("1");
//		set.add("2");
//		set.add("3");
//		String[] strs = set.toArray(new String[0]);
//		System.out.println(JSON.toJSONString(strs));

		Stream<int[]> stream = IntStream.rangeClosed(1, 100).boxed().flatMap(
			 a -> IntStream.rangeClosed(a, 100).filter(b -> Math.sqrt(a*a + b*b) % 1 == 0).mapToObj(
			 		b -> new int[]{a, b, (int)Math.sqrt(a*a + b*b)}
			 )
		);

		stream.limit(5).forEach(item -> System.out.println(
				item[0] + "," + item[1] + "," + item[2]
		));

		List<Integer> nums = Arrays.asList(1, 2, 3, 4);
		List<Integer> squareNums = nums.stream().
				map(n -> n * n).
				collect(Collectors.toList());

		System.out.println(">>>>>>>>>>>>>" + JSON.toJSONString(squareNums));

	}

	static Integer modifyTheValue2(int value, Function<Integer, Integer> function1, Function<Integer, Integer> function2){
		//value作为function1的参数，返回一个结果，该结果作为function2的参数，返回一个最终结果
		return  function1.andThen(function2).apply(value);
	}

}

class Person {

	private Integer id;
	private String name;

	public Person(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}



}