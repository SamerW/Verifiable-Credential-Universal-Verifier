#!/usr/bin/perl

use DateTime;
use Date::Parse;
use Switch;

my $log = "experiments.txt";

open(FILE,'<', $log) or die $!;

my $count = 0;

my @decision = ();
my @verify = ();
my @verifye = ();
my @trust = ();
my @policy = ();
my @policye = ();

my $startd = 0;
my $startv = 0;
my $startve = 0;
my $startt = 0;
my $startp = 0;
my $startpe = 0;

while (my $line = <FILE>) {
	chomp($line);
	# print "|$line|\n";
	(my $date, my $time, my $type, my $pid, $_) = split('\s+', $line);

	my $stime = "${date}T${time}";
	my $epoch = str2time($stime);

	switch ($line) {
		case m/## START DECISION ##/ {
			# print "$count START\n";
			$startd = $epoch;
		}
		case m/## END DECISION ##/ {
			my $dif = sprintf ("%.3f", $epoch - $startd);
			$decision[$count] = $dif;
			# print ("$count $startd $epoch $dif $msg\n");
			$startd = 0;
			$startv = 0;
			$startve = 0;
			$startt = 0;
			$startp = 0;
			$startpe = 0;
			$count++;
		}

		case m/\*2\* Begin: VPs Verification/ {
			$startv = $epoch;
		}
		case m/\*2\* End: VPs Verification/ {
			my $dif = sprintf ("%.3f", $epoch - $startv);
			$verify[$count] += $dif;
			# print ("$count $startv $epoch $dif $msg\n");
		}


		case m/\.4\.Calling Verifier API/ {
			$startve = $epoch;
		}
		case m/\.4\.Called Verifier API/ {
			my $dif = sprintf ("%.3f", $epoch - $startve);
			$verifye[$count] += $dif;
			# print ("$count $startve $epoch $dif $msg\n");
		}


		case m/\*3\* Begin: Trust Check/ {
			$startt = $epoch;
		}
		case m/\*3\* End: Trust Check/ {
			my $dif = sprintf ("%.3f", $epoch - $startt);
			$trust[$count] += $dif;
			# print ("$count $startt $epoch $dif $msg\n");
		}


		case m/\*4\* Begin: Policy Match/ {
			$startp = $epoch;
		}
		case m/\*4\* End: Policy Match/ {
			my $dif = sprintf ("%.3f", $epoch - $startp);
			$policy[$count] += $dif;
			# print ("$count $startp $epoch $dif $msg\n");
		}

		case m/\.1\. Begin: Call Policy Match API externally/ {
			$startpe = $epoch;
		}
		case m/\.1\. End: Call Policy Match API externally/ {
			my $dif = sprintf ("%.3f", $epoch - $startpe);
			$policye[$count] += $dif;
			# print ("$count $startpe $epoch $dif $msg\n");
		}
	}
}

close(FILE);

# for my $c (0 .. $#decision) {
# 	print "$c T: $decision[$c] v: $verify[$c] ve: $verifye[$c] t: $trust[$c] p: $policy[$c] pe: $policye[$c]\n";
# }

print "decision = [";
for my $c (0 .. $#decision) {
	if ($c) { print ", "; }
	my $text = $decision[$c] * 1000;
	print "$text";
}
print "]\n";

print "verify = [";
for my $c (0 .. $#verify) {
	if ($c) { print ", "; }
	my $text = $verify[$c] * 1000;
	print "$text";
}
print "]\n";

print "verifye = [";
for my $c (0 .. $#verifye) {
	if ($c) { print ", "; }
	my $text = $verifye[$c] * 1000;
	print "$text";
}
print "]\n";

print "trust = [";
for my $c (0 .. $#trust) {
	if ($c) { print ", "; }
	my $text = $trust[$c] * 1000;
	print "$text";
}
print "]\n";

print "policy = [";
for my $c (0 .. $#policy) {
	if ($c) { print ", "; }
	my $text = $policy[$c] * 1000;
	print "$text";
}
print "]\n";

print "policye = [";
for my $c (0 .. $#policye) {
	if ($c) { print ", "; }
	my $text = $policye[$c] * 1000;
	print "$text";
}
print "]\n";
