<?php

namespace AdminUserBundle;

use Symfony\Component\HttpKernel\Bundle\Bundle;

class AdminUserBundle extends Bundle
{
	//get parent FOSUserBundle
	public function getParent()
	{
		return 'FOSUserBundle';
	}
}

