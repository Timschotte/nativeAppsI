interface IWorker {

    fun work()

}

class Worker : IWork {

    override fun print() {
        //Do stuff
    }

}

class SuperWorker : IWork {

    override fun print() {
        //Do stuff
    }

}

class Manager(var worker: IWorker) {

	init{
		this.worker = worker
	}

    fun manager() {
        worker.work()
    }

}

This is better because we can add other types of workes without the need to change the managerclass