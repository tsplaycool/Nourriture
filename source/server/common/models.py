from django.contrib.auth.models import User
from django.db import models


class Supplier(models.Model):
    user = models.OneToOneField(User)
    name = models.CharField(max_length=20)
    country = models.CharField(max_length= 20)
    ingredients = models.CharField(max_length=100)


    def __unicode__(self):
        return self.user.username


class Gastronomist(models.Model):
    user = models.OneToOneField(User)
    openid = models.CharField(max_length=20)
    name = models.CharField(max_length=20)
    address = models.CharField(max_length=100)
    contact = models.CharField(max_length=20)
    health = models.CharField(max_length=20)

    def __unicode__(self):
        return self.user.username


class Ingredient(models.Model):
    name = models.CharField(max_length=20)
    alias = models.CharField(max_length=20)
    # place of production
    origin = models.CharField(max_length=30)
    # the category of the ingredient
    category=models.CharField(max_length=50)
    # the function of the ingredient
    function=models.CharField(max_length=100)
    # the retain freshness of the ingredient
    freshness=models.DateField(max_length=20)
    # detail description of ingredient
    nutrition = models.CharField(max_length=100)
    supplier = models.ManyToManyField(Supplier, blank=True)
    colour = models.CharField(max_length=20)
    flavour = models.CharField(max_length=60)
    shape = models.CharField(max_length=30)

    def __unicode__(self):
        return self.name


class Recipe(models.Model):
    name = models.CharField(max_length=20)
    ingredients = models.ManyToManyField(Ingredient)
    processing = models.TextField()
    nutrition = models.CharField(max_length=100)
    gastronomist = models.ForeignKey(Gastronomist)

    # added by linan --10.29
    # how many users have browsed this recipe
    browsecount = models.IntegerField()
    # how many users have collected this recipe
    collectcount=models.IntegerField()
    # this recipe is belong to which type of food,such as spicy or breakfast or other
    foodtype = models.CharField(max_length=20)
    # how long will it take to make this food
    makingtime = models.CharField(max_length=20)
    # some tips to make this food
    makingtip = models.TextField()
    def __unicode__(self):
        return self.name


class Product(models.Model):
    name = models.CharField(max_length=20)
    ingredients = models.ManyToManyField(Ingredient)
    nutrition = models.CharField(max_length=100)
    certification = models.CharField(max_length=20)
    stock = models.IntegerField()
    recipe = models.ManyToManyField(Recipe)

    def __unicode__(self):
        return self.name


class ProductTag(models.Model):
    name = models.CharField(max_length=20, unique=True)
    product = models.ManyToManyField(Product)

    def __unicode__(self):
        return self.name


class Favorite(models.Model):
    user = models.ForeignKey(User)
    collect_type = models.CharField(max_length=50)
    target_id = models.IntegerField()

    def __unicode__(self):
        return self.user.username


class Like(models.Model):
    user = models.ForeignKey(User)
    like_type = models.CharField(max_length=50)
    like_id = models.IntegerField()

    def __unicode__(self):
        return self.user.username


class Moment(models.Model):
    user = models.ForeignKey(User)
    description = models.CharField(max_length=200)
    media = models.CharField(max_length=100)

    def __unicode__(self):
        return self.user.username


class Comment(models.Model):
    user = models.ForeignKey(User)
    # the object commented upon
    target_type = models.IntegerField()
    # the object's id commented upon
    target_id = models.IntegerField()
    replay_comment_id = models.IntegerField()
    content = models.CharField(max_length=200)

    def __unicode__(self):
        return self.user.username